#include "mythread.hpp"

myThread::myThread(qintptr socketDescriptor, QObject *parent)
{
    this->socketDescriptor = socketDescriptor;
    socket = new QTcpSocket;
}

void myThread::run()
{
    socket->setSocketDescriptor(socketDescriptor);
    QThread::run();
}
