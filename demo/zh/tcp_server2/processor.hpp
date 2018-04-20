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

public slots:
    void work();
private:
    QTcpSocket *socket = nullptr;
};

#endif // PROCESSOR_HPP
