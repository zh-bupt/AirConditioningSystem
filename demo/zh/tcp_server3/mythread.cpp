#include "mythread.hpp"

myThread::myThread(qintptr socketDescriptor, QObject *parent)
{
    this->socketDescriptor = socketDescriptor;
    this->socket = new QTcpSocket();
    processor = new Processor(socket);
}

myThread::myThread()
{

}

void myThread::run()
{
    qDebug() << "Run ...";
    QThread::run();
}

void myThread::setSocket(qintptr socketDescriptor)
{
    this->socketDescriptor = socketDescriptor;
    this->socket = new QTcpSocket();
    socket->setSocketDescriptor(socketDescriptor);
    processor = new Processor(socket);
}

QTcpSocket* myThread::getSocket()
{
    return socket;
}

Processor* myThread::getProcessor()
{
    return processor;
}

void myThread::releaseConnect(QTcpSocket* socket)
{
    if(!socket) {
        delete socket;
        socket = nullptr;
    }
    if(!processor) {
        delete processor;
        processor = nullptr;
    }
}
