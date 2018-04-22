#ifndef MYSERVER_HPP
#define MYSERVER_HPP

#include <QTcpServer>
#include <QObject>
#include <QTcpSocket>
#include <mythread.hpp>
#include <QMap>
#include "threadpool.hpp"

class myServer : public QTcpServer
{
public:
    myServer(QObject *parent);
    void broadCast();
    void send(QString id);

protected:
    void incomingConnection(qintptr socketDescriptor) override;
private slots:
    void addSocket(QString id, QTcpSocket *socket);
    void deleteSocket(QString id);
    void login();
private:
    QMap<QString, QTcpSocket*> socketMap;
    QTcpSocket *socket;
    Threadpool *pool = nullptr;
};

#endif // MYSERVER_HPP
