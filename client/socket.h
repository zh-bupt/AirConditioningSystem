#ifndef SOCKET_H
#define SOCKET_H
#include "QTcpSocket"
#include "QString"


class Socket
{
public:
    static Socket* Instance();
    QTcpSocket* getSocket();
    //void static write(QByteArray info);
protected:
      Socket();
private:

    static Socket* _instance;
    QTcpSocket *qtcpSocket = nullptr;

};

#endif // SOCKET_H
