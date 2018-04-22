#include "myserver.hpp"
#include "processor.hpp"

myServer::myServer(QObject *parent) : QTcpServer(parent)
{
    pool = new Threadpool();
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

void myServer::deleteSocket(QString id)
{
    socketMap.remove(id);
}

void myServer::login()
{
    QString id = socket->readAll();
    socketMap.insert(id, socket);
    disconnect(socket, SIGNAL(readyRead()), this, SLOT(login()));
}

void myServer::broadCast()
{
    foreach (QTcpSocket *socket, socketMap) {
        socket->write(QString("Broadcast info!").toUtf8());
    }
}

void myServer::send(QString id)
{
    if (socketMap.contains(id)) socketMap.value(id)->write(QString("Targeted info!").toUtf8());
}
