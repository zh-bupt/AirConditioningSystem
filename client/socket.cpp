#include "socket.h"
#include "QTcpSocket"
#include "QHostAddress"



Socket::Socket()
{
    this->qtcpSocket = new QTcpSocket();
    qtcpSocket->connectToHost(QHostAddress("10.206.13.14"), 6666);
}
Socket* Socket::Instance()
{
    if(_instance==0){
        _instance=new Socket();
    }
    return _instance;
}

QTcpSocket* Socket::getSocket(){
    return this->qtcpSocket;
}
Socket* Socket::_instance=0;

//void static Socket::write(QByteArray info){
//    this->qtcpSocket->write(info);
//}
