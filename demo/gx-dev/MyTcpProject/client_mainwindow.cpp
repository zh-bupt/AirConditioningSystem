#include "client_mainwindow.h"
#include "ui_client_mainwindow.h"

Client_MainWindow::Client_MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Client_MainWindow)
{
    ui->setupUi(this);

    ui->lineEdit_IP->setText("127.0.0.1");
    ui->lineEdit_Port->setText("1234");
    ui->pushButton_Send->setEnabled(false);

    socket = new QTcpSocket(this);

    connect(socket,SIGNAL(readyRead()),this,SLOT(readyRead()));
    connect(socket,SIGNAL(disconnected()),this,SLOT(disconnected()));


}

Client_MainWindow::~Client_MainWindow()
{
    delete ui;
}

void Client_MainWindow::closeEvent(QCloseEvent *event)
{
    if(ui->pushButton_Send->isEnabled())
    {
        if(socket->state() == QAbstractSocket::ConnectedState)
        {
            socket->disconnectFromHost();
        }
        ui->pushButton_Send->setEnabled(false);
    }
}

void Client_MainWindow::on_pushButton_Connect_clicked()
{

    if(ui->pushButton_Connect->text()=="连接")
    {
        QString IP = ui->lineEdit_IP->text();
        int port = ui->lineEdit_Port->text().toInt();
        socket->abort();
        socket->connectToHost(IP,port);
        if(!socket->waitForConnected(30000))
        {
            qDebug()<<"Connection failed!";
            return;
        }
        qDebug()<<"Connection successfully!";
        ui->pushButton_Send->setEnabled(true);
        ui->pushButton_Connect->setText("断开连接");
    }
    else
    {
        socket->disconnectFromHost();
        ui->pushButton_Connect->setText("连接");
        ui->pushButton_Send->setEnabled(false);
    }

}

void Client_MainWindow::readyRead()
{
    QByteArray buffer;
    buffer = socket->readAll();
    if(!buffer.isEmpty())
    {
        QString str = ui->textEdit_Recv->toPlainText();
        str+="Server: "+tr(buffer)+"\n";
        ui->textEdit_Recv->setText(str);
    }

    QTextCursor cursor=ui->textEdit_Recv->textCursor();
    cursor.movePosition(QTextCursor::End);
    ui->textEdit_Recv->setTextCursor(cursor);
}

void Client_MainWindow::disconnected()
{
    ui->pushButton_Send->setEnabled(false);
    ui->pushButton_Connect->setText("连接");
    qDebug()<<"disconnected!";
}

void Client_MainWindow::on_pushButton_Send_clicked()
{
    socket->write(ui->textEdit_Send->toPlainText().toUtf8());
    QString str = ui->textEdit_Recv->toPlainText();
    str+="Client: "+ui->textEdit_Send->toPlainText()+"\n";
    ui->textEdit_Recv->setText(str);
    socket->flush();
    ui->textEdit_Send->setText("");

    QTextCursor cursor=ui->textEdit_Recv->textCursor();
    cursor.movePosition(QTextCursor::End);
    ui->textEdit_Recv->setTextCursor(cursor);
}
