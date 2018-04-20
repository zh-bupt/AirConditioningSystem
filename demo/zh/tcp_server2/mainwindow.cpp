#include "mainwindow.hpp"
#include "ui_mainwindow.h"
#include <QList>
#include <QtNetwork>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    server = new myServer();
    if (!server->listen(QHostAddress::LocalHost, 6666))
    {
        close();
        return;
    }

    qDebug() << server->serverAddress() << ":" << server->serverPort();

//    QString ipAddress;
//    QList<QHostAddress> ipAddressesList = QNetworkInterface::allAddresses();
//    // use the first non-localhost IPv4 address
//    for (int i = 0; i < ipAddressesList.size(); ++i) {
//        if (ipAddressesList.at(i) != QHostAddress::LocalHost &&
//            ipAddressesList.at(i).toIPv4Address()) {
//            ipAddress = ipAddressesList.at(i).toString();
//            break;
//        }
//    }
//    // if we did not find one, use IPv4 localhost
//    if (ipAddress.isEmpty())
//        ipAddress = QHostAddress(QHostAddress::LocalHost).toString();
//    qDebug() << tr("The server is running on\n\nIP: %1\nport: %2\n\n"
//                            "Run the Fortune Client example now.")
//                         .arg(ipAddress).arg(server.serverPort());
}

MainWindow::~MainWindow()
{
    delete ui;
}
