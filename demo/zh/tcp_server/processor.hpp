#ifndef PROCESSOR_HPP
#define PROCESSOR_HPP

#include <QObject>
#include <QTcpSocket>

class Processor : public QObject
{
    Q_OBJECT
public:
    explicit Processor(QTcpSocket* socket, QObject *parent = nullptr);
    bool login(QString id, QString password);
    void disconnect(QString);

public slots:
    void work();

private:
    QTcpSocket *socket = nullptr;
    QString id;
};

#endif // PROCESSOR_HPP
