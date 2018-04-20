#include "server_mainwindow.h"
#include "ui_server_mainwindow.h"

Server_MainWindow::Server_MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Server_MainWindow)
{
    ui->setupUi(this);

    ui->lineEdit_Port->setText("1234");
    ui->pushButton_Send->setEnabled(false);

    server = new QTcpServer(this);

    connect(server,SIGNAL(newConnection()),this,SLOT(newConnection()));
}

Server_MainWindow::~Server_MainWindow()
{
    delete ui;
}

void Server_MainWindow::closeEvent(QCloseEvent *event)
{
    if(ui->pushButton_Listen->text()=="取消侦听")
    {
        if(socket->state() == QAbstractSocket::ConnectedState)
        {
            socket->disconnectFromHost();
        }
        server->close();
        ui->pushButton_Listen->setText("侦听");
        ui->pushButton_Send->setEnabled(false);
    }
}
void Server_MainWindow::newConnection()
{
    socket = server->nextPendingConnection();

    connect(socket,SIGNAL(readyRead()),this,SLOT(readyRead()));
    connect(socket,SIGNAL(disconnected()),this,SLOT(disconnected()));

    ui->pushButton_Send->setEnabled(true);

    qDebug() << "A client connected!";
}

void Server_MainWindow::readyRead()
{
    QByteArray buffer;
    buffer = socket->readAll();
    if(!buffer.isEmpty())
    {
        QString str = ui->textEdit_Recv->toPlainText();
        str+="Client: "+tr(buffer)+"\n";
        ui->textEdit_Recv->setText(str);
    }

    QTextCursor cursor=ui->textEdit_Recv->textCursor();
    cursor.movePosition(QTextCursor::End);
    ui->textEdit_Recv->setTextCursor(cursor);
}

void Server_MainWindow::disconnected()
{
    ui->pushButton_Send->setEnabled(false);
    qDebug()<<"disconnected!";
}

void Server_MainWindow::on_pushButton_Listen_clicked()
{
    if(ui->pushButton_Listen->text()=="侦听")
    {
        int port = ui->lineEdit_Port->text().toInt();
        if(!server->listen(QHostAddress::Any,port))
        {
            qDebug()<<server->errorString();
            return;
        }
        ui->pushButton_Listen->setText("取消侦听");
        qDebug()<<"Listen successfully!";
    }
    else
    {
        if(socket != Q_NULLPTR)
        {
            if(socket->state() == QAbstractSocket::ConnectedState)
                socket->disconnectFromHost();
        }
        server->close();
        ui->pushButton_Listen->setText("侦听");
        ui->pushButton_Send->setEnabled(false);
    }


}

void Server_MainWindow::on_pushButton_Send_clicked()
{

    socket->write(ui->textEdit_Send->toPlainText().toUtf8());
    QString str = ui->textEdit_Recv->toPlainText();
    str+="Server: "+ui->textEdit_Send->toPlainText()+"\n";
    ui->textEdit_Recv->setText(str);
    socket->flush();
    ui->textEdit_Send->setText("");

    QTextCursor cursor=ui->textEdit_Recv->textCursor();
    cursor.movePosition(QTextCursor::End);
    ui->textEdit_Recv->setTextCursor(cursor);
}
