#include "mythread.h"

MyThread::MyThread(int ID, QObject *parent) : QThread(parent)
{
    this->socketDescriptor = ID;
}

void MyThread::readyRead()
{
    QByteArray Data = socket->readAll();
    qDebug() << socketDescriptor << "Data in:"<<Data;

    emit readyRead(Data,socketDescriptor);
}

void MyThread::disconnected()
{
    qDebug() << socketDescriptor << "Disconnected";
    socket->deleteLater();
    exit(0);
}

void MyThread::run()
{
    qDebug() << socketDescriptor<<"Starting thread...";
    socket = new MySocket();
    if(!socket->setSocketDescriptor(this->socketDescriptor))
    {
        emit error1(socket->error());
        return;
    }

    connect(socket,SIGNAL(readyRead()),this,SLOT(readyRead()),Qt::DirectConnection);
    connect(socket,SIGNAL(disconnected()),this,SLOT(disconnected()),Qt::DirectConnection);
    connect(this,SIGNAL(sendMsg1(QByteArray,int)),socket,SLOT(sendMsg(QByteArray,int)));
    qDebug() << socketDescriptor<<"Client Connected...";

    exec();
}

void MyThread::sendMsg(QByteArray Data,int socketDescriptor)
{
    emit sendMsg1(Data,socketDescriptor);
}
