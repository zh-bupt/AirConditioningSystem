#ifndef MYSERVER_HPP
#define MYSERVER_HPP

#include <QTcpServer>
#include <mythread.hpp>

class myServer : public QTcpServer
{
public:
    myServer();
protected:
    void incomingConnection(qintptr socketDescriptor) override;
};

#endif // MYSERVER_HPP
