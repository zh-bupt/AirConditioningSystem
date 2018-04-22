#include "mysocket.h"

MySocket::MySocket(QObject *parent) : QTcpSocket(parent)
{

}

void MySocket::sendMsg(QByteArray Data,int socketDescriptor)
{
    if(this->socketDescriptor() == socketDescriptor)
        this->write(Data);
}
