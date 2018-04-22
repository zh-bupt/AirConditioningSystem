#ifndef MYTHREAD_H
#define MYTHREAD_H

#include <QObject>
#include <QThread>
#include <QTcpSocket>
#include <QDebug>
#include "mysocket.h"

class MyThread : public QThread
{
    Q_OBJECT
public:
    explicit MyThread(int ID, QObject *parent = nullptr);
    void run();
signals:
    void error1(QTcpSocket::SocketError socketerror);
    void readyRead(QByteArray Data,int socketDescriptor);
    void sendMsg1(QByteArray Data,int socketDescriptor);

public slots:
    void readyRead();
    void disconnected();
    void sendMsg(QByteArray Data, int socketDescriptor);
private:
    QTcpSocket *socket;
    int socketDescriptor;
};

#endif // MYTHREAD_H
