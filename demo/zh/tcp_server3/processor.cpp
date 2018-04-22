#include "processor.hpp"

Processor::Processor(QTcpSocket *socket, QObject *parent) : QObject(parent)
{
    this->socket = socket;
}

void Processor::work()
{
    QString info = socket->readAll();
    emit login(info, socket);
    id = info;
    qDebug() << info << "\n";
    QString ans = info + "processed!";
    socket->write(ans.toUtf8());
}

void Processor::quit()
{
    emit disconnect(id);
}
