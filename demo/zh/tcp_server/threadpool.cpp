#include "threadpool.hpp"
#include <mythread.hpp>
#include <QTcpSocket>
#include "myserver.hpp"

Threadpool::Threadpool(QObject *parent) : QObject(parent)
{
    for(int i = 0; i < max_threads; i++)
    {
        myThread *thread = new myThread();
        thread->start();
        free_queue.enqueue(thread);
    }
    qDebug() << "Thread num:" << free_queue.size();
}

myThread* Threadpool::fetch(qintptr socketDesciptor)
{
    if(free_queue.isEmpty()) return nullptr;
    myThread *thread = free_queue.dequeue();
    thread->setSocket(socketDesciptor);
    busy.insert(thread->getSocket(), thread);
    return thread;
}

void Threadpool::giveBack()
{
    qDebug() << "Give back";
    QTcpSocket* socket = dynamic_cast<QTcpSocket*>(sender());
    qintptr descriptor = socket->socketDescriptor();
    qDebug() << socket;
    if(busy.contains(socket))
    {
        myThread *thread = busy.value(socket);
        free_queue.enqueue(thread);
        busy.remove(socket);
        myServer::getGlobalInstance()->deleteSocket(socket);
    } else {
        qDebug() << "No such socket!";
    }
}
