#include "mainwindow.hpp"
#include "ui_mainwindow.h"
#include <QJsonObject>
#include <QJsonDocument>
#include <string>
#include <QDebug>
#include <iostream>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    setWindowTitle("Client");
    ui->setupUi(this);
    init();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::init()
{
    socket = new QTcpSocket(this);
    newConnect();
    connect(socket, SIGNAL(readyRead()), this, SLOT(receiveData()));
    connect(socket, SIGNAL(error(QAbstractSocket::SocketError)),
            this, SLOT(displayError(QAbstractSocket::SocketError)));
}

void MainWindow::receiveData()
{
    QString data = socket->readAll();
    qDebug() << data << '\n';
//    ui->infoTb->setText(data);
}

void MainWindow::newConnect()
{
    socket->abort();
    socket->connectToHost("127.0.0.1", 6666);
}

void MainWindow::displayError(QAbstractSocket::SocketError)
{
    qDebug() << socket->errorString();
    socket->close();
}

void MainWindow::on_loginBtn_clicked()
{
    QString id = ui->idLe->text();
    QString password = ui->pwLe->text();
    login(id, password);
}

void MainWindow::login(QString id, QString password)
{
    QJsonObject obj;
    obj.insert("type", "login");
    obj.insert("room_id", id);
    obj.insert("id", password);

    QJsonDocument document;
    document.setObject(obj);
    std::string byteArray = document.toJson(QJsonDocument::Compact).toStdString();
    std::string head = getHead(byteArray.length());
    std::string data = head + byteArray;
    qDebug() << QString::fromStdString(data);
    socket->write(QString::fromStdString(data).toUtf8());
}

std::string MainWindow::getHead(int length) {
    if (length > 9999) return "9999";
    if (length <= 0) return "0000";
    std::string result = "0000";
    int i = 3;
    while(length > 0) {
        result[i] = '0' + length % 10;
        length = length / 10;
        i--;
    }
    return result;
}

void MainWindow::on_pushButton_clicked()
{
    std::string str = ui->infoTB->toPlainText().toStdString();
    str = getHead(str.length()) + str;
    socket->write(QString::fromStdString(str).toUtf8());
}
