#ifndef MYSERVER_HPP
#define MYSERVER_HPP

#include <QTcpServer>
#include <mythread.hpp>
#include <QThreadPool>

class myServer : public QTcpServer
{
public:
    myServer(QObject *parent);
protected:
    void incomingConnection(qintptr socketDescriptor) override;
private:
};

#endif // MYSERVER_HPP
