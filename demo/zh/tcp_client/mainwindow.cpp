#include "mainwindow.hpp"
#include "ui_mainwindow.h"
#include <QJsonObject>
#include <QJsonDocument>

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
    ui->infoTb->setText(data);
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
    obj.insert("id", id);
    obj.insert("password", password);

    QJsonDocument document;
    document.setObject(obj);
    QByteArray byteArray = document.toJson(QJsonDocument::Compact);
    socket->write(byteArray);
}
