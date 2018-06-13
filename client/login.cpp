#include "login.h"
#include "ui_login.h"
#include "QMessageBox"
#include "QObject"

Login::Login(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Login)
{
    ui->setupUi(this);
    init();
    connectServer();
   // QObject::connect(socket, &QTcpSocket::readyRead, this, &Login::socket_Read_Data);
   // QObject::connect(socket, &QTcpSocket::disconnected, this, &Login::socket_Disconnected);

}

Login::~Login()
{
    delete this->tcpSocket;
    delete ui;
}
void Login::init()
{
    tcpSocket=new QTcpSocket(this);
    connect(tcpSocket,SIGNAL(error(QAbstractSocket::SocketError)),
            this,SLOT(displayError(QAbstractSocket::SocketError)));   //发生错误时执行displayError函数
}
void Login::connectServer()
{
    tcpSocket->abort();   //取消已有的连接
    tcpSocket->connectToHost(QHostAddress("127.0.0.1"),3333);
    connect(tcpSocket,SIGNAL(readyRead()),this,SLOT(readMessages()));
}
void Login::on_login_clicked()
{
    QString userName=ui->account_number_in->text();
    QString passward=ui->password_in->text();
    if(userName=="" || passward=="")
         QMessageBox::information(this,"Warning","Input is empty!",QMessageBox::Ok);
    QString bs="b";
    QString data=bs+"#"+userName+"#"+passward;
    tcpSocket->write(data.toLatin1());

    /*if(ui->account_number_in->text().trimmed() == tr("sjw") &&
             ui->password_in->text().trimmed() == tr("123456"))
      {
         accept();
      }
    else {
         QMessageBox::warning(this, "Warning!","User name or password error!", QMessageBox::Yes);
         ui->account_number_in->clear();
         ui->password_in->clear();
         ui->account_number_in->setFocus();*/
    }

/*void Login::socket_Read_Data()
{
    QByteArray buffer;
    //读取缓冲区数据
    buffer = socket->readAll();
    if(!buffer.isEmpty())
    {
        QString str; //= ui->textEdit_Recv->toPlainText();
        str+=tr(buffer);
        //刷新显示
       // ui->textEdit_Recv->setText(str);
    }
}*/
void Login::displayError(QAbstractSocket::SocketError)
{
    qDebug()<<tcpSocket->errorString();   //输出出错信息
}
/*void Login::socket_Disconnected()
{
    //发送按键失能
    //ui->pushButton_Send->setEnabled(false);
    //修改按键文字
//    ui->pushButton_Connect->setText("连接");
   // qDebug() << "Disconnected!";
}*/
void Login::readMessages()
{
    QString data=tcpSocket->readAll();
    QStringList list=data.split("#");
    if(list[0]=="b" && list[1]=="true")
        QMessageBox::information(this,"信息提示","登录成功!",QMessageBox::Ok);
    else if(list[0]=="b" && list[1]=="false")
            QMessageBox::information(this,"信息提示","登录失败,用户名或密码错误!",QMessageBox::Ok);
    else
        return;
}
