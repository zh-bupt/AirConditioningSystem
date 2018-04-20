#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    mServer = new Server_MainWindow(this);
    mServer->show();
}

void MainWindow::on_pushButton_2_clicked()
{
    mClient = new Client_MainWindow(this);
    mClient->show();
}


