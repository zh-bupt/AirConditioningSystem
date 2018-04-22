#ifndef MYSOCKET_H
#define MYSOCKET_H

#include <QObject>
#include <QTcpSocket>

class MySocket : public QTcpSocket
{
    Q_OBJECT
public:
    explicit MySocket(QObject *parent = nullptr);

signals:

public slots:
    void sendMsg(QByteArray Data,int socketDescriptor);
private:

};

#endif // MYSOCKET_H
