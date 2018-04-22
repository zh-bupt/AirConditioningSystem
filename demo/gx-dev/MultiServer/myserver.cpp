#include "myserver.h"

MyServer::MyServer(QObject *parent) : QTcpServer(parent)
{
    mainwindow = dynamic_cast<MainWindow *>(parent);
}

void MyServer::incomingConnection(int socketDescriptor)
{
    qDebug() << socketDescriptor << " connecting...";
    MyThread *thread = new MyThread(socketDescriptor);
    connect(thread,SIGNAL(finished()),thread,SLOT(deleteLater()));
    connect(thread,SIGNAL(readyRead(QByteArray,int)),mainwindow,SLOT(readyRead(QByteArray,int)));
    connect(mainwindow,SIGNAL(sendMsg(QByteArray,int)),thread,SLOT(sendMsg(QByteArray,int)));
    thread->start();
}
