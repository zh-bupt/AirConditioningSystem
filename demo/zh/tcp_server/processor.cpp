#include "processor.hpp"
#include "myserver.hpp"
#include <QJsonDocument>
#include <QJsonObject>

Processor::Processor(QTcpSocket *socket, QObject *parent) : QObject(parent)
{
    this->socket = socket;
}

void Processor::work()
{
    QByteArray byteArray = socket->readAll();
    QJsonParseError error;
    QJsonDocument document = QJsonDocument::fromJson(byteArray, &error);
    if (!document.isNull() && (error.error == QJsonParseError::NoError))
    {
        if (document.isObject())
        {
            QJsonObject obj = document.object();
            if (obj.contains("type"))
            {
                if (obj.value("type").toString() == "login") {
                    if(login(obj.value("id").toString(), obj.value("password").toString()))
                        socket->write(QString("登录成功").toUtf8());
                    else
                        socket->write(QString("登录失败").toUtf8());
                }
            }
        }
    }
}

bool Processor::login(QString id, QString password)
{
    if (id == "123" && password == "123")
    {
        myServer::getGlobalInstance()->addSocket(id, socket);
        return true;
    }
    if (id == "124" && password == "124")
    {
        myServer::getGlobalInstance()->addSocket(id, socket);
        return true;
    }
    return false;
}

