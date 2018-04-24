#ifndef MYSERVER_HPP
#define MYSERVER_HPP

#include <QTcpServer>
#include <QObject>
#include <QTcpSocket>
#include <mythread.hpp>
#include <QHash>
#include "threadpool.hpp"

class myServer : public QTcpServer
{
public:
    static myServer* getGlobalInstance();
    void broadCast(QString info);
    void send(QString id, QString info);
    void addSocket(QString id, QTcpSocket *socket);
    void deleteSocket(QTcpSocket *socket);

protected:
    void incomingConnection(qintptr socketDescriptor) override;

private:
    myServer();
    static myServer* server;
    QHash<QString, QTcpSocket*> socketMap;
    QTcpSocket *socket;
    Threadpool *pool = nullptr;
};

#endif // MYSERVER_HPP
