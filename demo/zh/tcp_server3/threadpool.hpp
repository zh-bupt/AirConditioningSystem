#ifndef THREADPOOL_HPP
#define THREADPOOL_HPP

#include <QObject>
#include <QHash>
#include <QQueue>
#include <QHash>
#include "mythread.hpp"

class Threadpool : public QObject
{
    Q_OBJECT
public:
    explicit Threadpool(QObject *parent = nullptr);
    myThread *fetch(qintptr socketDesciptor);

signals:

public slots:
    void giveBack();

private:
    int max_threads = 50;
    QHash<QTcpSocket*,myThread*> busy;
    QQueue<myThread*> free_queue;
};

#endif // THREADPOOL_HPP
