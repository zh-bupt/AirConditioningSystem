#include "mainwindow.hpp"
#include "ui_mainwindow.h"

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
    this->ui->lineEdit->setText(data);
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

void MainWindow::on_pushButton_clicked()
{
    socket->write(ui->lineEdit->text().toUtf8());
}
