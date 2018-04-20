#include "processor.hpp"

Processor::Processor(QTcpSocket *socket, QObject *parent) : QObject(parent)
{
    this->socket = socket;
}

void Processor::work()
{
    QString info = socket->readAll();
    qDebug() << info << "\n";
    QString ans = info + "processed!";
    socket->write(ans.toUtf8());
}
