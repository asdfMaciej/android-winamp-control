import socket
import sys
import winamp
from _thread import *


class SocketServer:
	def __init__(self, port=21337):
		self.port = port
		self.host = ''
		self.sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self._w = WinampHandler()

	def run(self):
		try:
			self.sck.bind((self.host, self.port))
		except socket.error as msg:
			print('Bind failed. Error Code : '
				+ str(msg[0]) + ' Message ' + msg[1])
			sys.exit()
		self.sck.listen(10)

		while 1:
			conn, addr = self.sck.accept()
			print('Connected with ' + addr[0] + ':' + str(addr[1]))
			start_new_thread(self.handle, (conn,))

		self.sck.close()

	def handle(self, connection):
		connection.send('connected'.encode('utf-8'))
		while True:
			data = connection.recv(1024).decode('utf-8')
			if not data: 
				break
			connection.send(str(self._w.parse(data)).encode('utf-8'))
		connection.close()

class WinampHandler:
	def __init__(self):
		self.w = winamp.winamp()

	def parse(self, txt):
		if not txt:
			return False

		commands = txt.split(' ')
		cmd = commands[0]
		winamp_commands = (
			'prev', 'next', 'play', 'pause', 'stop',
			'fadeout', 'forward', 'rewind', 'raisevol', 'lowervol'
		)

		if cmd in winamp_commands:
			self.w.command(cmd)
		elif cmd == 'getPlayingStatus':
			return self.w.getPlayingStatus()
		elif cmd == 'getCurrentTrackName':
			return self.w.getCurrentTrackName()
		elif cmd == 'setVolume':
			if len(commands) == 2:
				if commands[1].isdigit():
					vol = int(commands[1])
					self.w.setVolume(vol)
					return True
				else:
					return False
			else:
				return False
		else:
			return False
		return True


if __name__ == "__main__":
	SocketServer().run()