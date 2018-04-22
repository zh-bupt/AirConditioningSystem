#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    Server = new MyServer(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::readyRead(QByteArray Data,int socketDescriptor)
{
    QString str = ui->textEdit->toPlainText().toUtf8();
    str+="Client"+QString::number(socketDescriptor)+": "+tr(Data)+"\n";
    ui->textEdit->setText(str);
}

void MainWindow::on_pushButton_clicked()
{
    emit sendMsg(ui->textEdit_Send->toPlainText().toUtf8(),ui->lineEdit_ID->text().toInt());
}

void MainWindow::on_pushButton_Listen_clicked()
{
    if(ui->pushButton_Listen->text()=="侦听")
    {
        int port = ui->lineEdit_Port->text().toInt();
        if(!Server->listen(QHostAddress::Any,port))
        {
            qDebug()<<Server->errorString();
            return;
        }
        ui->pushButton_Listen->setText("取消侦听");
        qDebug()<<"Listen successfully!";
    }
    else
    {
//        if(socket != Q_NULLPTR)
//        {
//            if(socket->state() == QAbstractSocket::ConnectedState)
//                socket->disconnectFromHost();
//        }
        Server->close();
        ui->pushButton_Listen->setText("侦听");
        ui->pushButton->setEnabled(false);
    }
}
