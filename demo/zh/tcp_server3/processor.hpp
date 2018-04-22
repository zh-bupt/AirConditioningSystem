#ifndef PROCESSOR_HPP
#define PROCESSOR_HPP

#include <QObject>
#include <QTcpSocket>

class Processor : public QObject
{
    Q_OBJECT
public:
    explicit Processor(QTcpSocket* socket, QObject *parent = nullptr);

signals:
    void login(QString, QTcpSocket*);
    void disconnect(QString);

public slots:
    void work();
    void quit();

private:
    QTcpSocket *socket = nullptr;
    QString id;
};

#endif // PROCESSOR_HPP
