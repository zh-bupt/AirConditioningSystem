#ifndef MYTHREAD_HPP
#define MYTHREAD_HPP

#include <QThread>
#include <QTcpSocket>
#include <processor.hpp>
#include <QHash>

class myThread : public QThread
{
public:
    myThread(qintptr socketDescriptor, QObject* parent);
    myThread();
    void run() override;
    void setSocket(qintptr socketDescriptor);
    QTcpSocket* getSocket();
    Processor* getProcessor();
    void releaseConnect(QTcpSocket* socket);

private:
    qintptr socketDescriptor;
    QTcpSocket *socket = nullptr;
    QString id;
    Processor *processor = nullptr;
    QHash<qintptr, QTcpSocket*> socket_map;

};

#endif // MYTHREAD_HPP
