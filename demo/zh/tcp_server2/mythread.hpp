#ifndef MYTHREAD_HPP
#define MYTHREAD_HPP

#include <QThread>
#include <QTcpSocket>
#include <QRunnable>

class myThread : public QThread
{
public:
    myThread(qintptr socketDescriptor, QObject* parent);
    void run() override;
    QTcpSocket *socket = nullptr;

private:
    int socketDescriptor;
};

#endif // MYTHREAD_HPP
