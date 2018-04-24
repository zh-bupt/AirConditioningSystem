#include "myserver.hpp"
#include "processor.hpp"

myServer* myServer::server = nullptr;

myServer::myServer() : QTcpServer(nullptr)
{
    pool = new Threadpool();
}

myServer* myServer::getGlobalInstance()
{
    if(!server) server = new myServer();
    return server;
}

void myServer::incomingConnection(qintptr socketDescriptor)
{
//    myThread *thread = new myThread(socketDescriptor, this);
//    Processor *processor = new Processor(thread->getSocket());
//    connect(thread->getSocket(), SIGNAL(readyRead()), thread->getProcessor(), SLOT(work()));
//    connect(thread->getSocket(), SIGNAL(disconnected()), thread, SLOT(quit()));
//    connect(thread->getProcessor(), SIGNAL(login(QString,QTcpSocket*)), this, SLOT(addSocket(QString,QTcpSocket*)));
//    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
//    thread->start();
    myThread *thread = pool->fetch(socketDescriptor);
    if (thread){
        connect(thread->getSocket(), SIGNAL(readyRead()), thread->getProcessor(), SLOT(work()));
        connect(thread->getSocket(), SIGNAL(disconnected()), pool, SLOT(giveBack()));
        thread->start();
    } else {
        qDebug() << "No thread!!";
    }
}

void myServer::addSocket(QString id, QTcpSocket *socket)
{
    qDebug() << "Add a client!";
    socketMap.insert(id, socket);
}

void myServer::deleteSocket(QTcpSocket *socket)
{
    for(auto it =  socketMap.begin(); it != socketMap.end(); it++)
    {
        if(it.value() == socket){
            socketMap.remove(it.key());
            break;
        }
    }
}


void myServer::broadCast(QString info)
{
    if (!info.isEmpty()){
        foreach (QTcpSocket *socket, socketMap) {
            socket->write(info.toUtf8());
        }
    }
}

void myServer::send(QString id, QString info)
{
    if (!info.isEmpty()){
        if (socketMap.contains(id)) socketMap.value(id)->write(info.toUtf8());
    }
}
