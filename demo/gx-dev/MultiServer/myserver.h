#ifndef MYSERVER_H
#define MYSERVER_H

#include <QObject>
#include <QTcpServer>
#include <QDebug>
#include "mythread.h"
#include "mainwindow.h"
class MainWindow;
class MyServer : public QTcpServer
{
    Q_OBJECT
public:
    explicit MyServer(QObject *parent = nullptr);
protected:
    void incomingConnection(int socketDescriptor);
private:
    MainWindow *mainwindow;
signals:

};

#endif // MYSERVER_H
