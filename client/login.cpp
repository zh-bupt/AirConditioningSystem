#include "login.h"
#include "ui_login.h"
#include "QMessageBox"
#include "QObject"
#include "QJsonDocument"
#include "QJsonObject"
#include "header.h"
#include "socket.h"
#include <string>

Login::Login(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Login)
{
    ui->setupUi(this);
//    Socket tempsocket;
    Socket *socket =Socket::Instance();
    this->tcpSocket = socket->getSocket();
   init();
   connectServer();
    //this->parent = (MainWindow*)parent;
   // QObject::connect(socket, &QTcpSocket::readyRead, this, &Login::socket_Read_Data);
   // QObject::connect(socket, &QTcpSocket::disconnected, this, &Login::socket_Disconnected);

}

Login::~Login()
{
    //this->tcpSocket=null;
    delete ui;
    disconnect (tcpSocket,SIGNAL(readyRead()),this,SLOT(readMessages()));
}
void Login::init()
{
    //tcpSocket=new QTcpSocket(this);
//    QTcpSocket *tcpSocket=socket::Instance()->getSocket();
    //QTcpSocket *tcpSocket = parent->getSocket();
    connect(tcpSocket,SIGNAL(error(QAbstractSocket::SocketError)),
            this,SLOT(displayError(QAbstractSocket::SocketError)));   //发生错误时执行displayError函数
}
void Login::connectServer()
{
    //tcpSocket->abort();   //取消已有的连接
   // tcpSocket->connectToHost(QHostAddress("10.206.13.19"),6666);
   connect(tcpSocket,SIGNAL(readyRead()),this,SLOT(readMessages()));
}
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
void Login::on_login_clicked()
{
    QString room_id=ui->account_number_in->text();
    QString id=ui->password_in->text();
    if(room_id=="" || id=="")
    { QMessageBox::information(this,"Warning","Input is empty!",QMessageBox::Ok);
    ui->account_number_in->clear();
    ui->password_in->clear();
    ui->account_number_in->setFocus();}
    else{                //当账号或者密码为空时就不再发送。
    QJsonObject simp_ayjson;
       simp_ayjson.insert("type", "login");
       simp_ayjson.insert("room_id",room_id );
       simp_ayjson.insert("id", id);
       QJsonDocument document;
       document.setObject(simp_ayjson);
       QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
       std::string data = QString(simpbyte_array).toStdString();
       std::string login_sent= Header::getHead(data.length())+data;
       qDebug() << "简单的QTJson数据：" << QString::fromStdString(login_sent);
       //Socket::write(QString::fromStdString(login_sent).toUtf8());
       tcpSocket->write(QString::fromStdString(login_sent).toUtf8());
        //ui->login->setEnabled(false);
    }
    //accept();//测试用例
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

void Login::readMessages()
{
    QString data=tcpSocket->readAll();
    //QString data = "{\"type\":\"login_ack\",\"result\":\"succeed\"}";




   // std::string data=tcpSocket->readAll();
    qDebug() << data;
    data = data.mid(4);
    qDebug() << data;
    QJsonParseError simp_json_error;
    QString type,result,mode;
             // std::string data1=QString(QString::fromStdString((data)).toUtf8()).toStdString();
             // QString simpjson_str= QString::fromStdString(data);
    QString simpjson_str=data;
            QJsonDocument simp_parse_doucment = QJsonDocument::fromJson(simpjson_str.toUtf8(), &simp_json_error);
            //检查json是否有错误
            if (simp_json_error.error == QJsonParseError::NoError)
            {
                if (simp_parse_doucment.isObject())
                {
                    //开始解析json对象
                    QJsonObject obj = simp_parse_doucment.object();
                    //如果包含type
                    if (obj.contains("type"))
                    {
                        //的到type
                        QJsonValue type_value = obj.take("type");
                        if (type_value.isString())
                        {
                            //转换type
                            type = type_value.toVariant().toString();
                        }
                    }
                    if (obj.contains("result"))
                    {
                        //的到result
                        QJsonValue result_value = obj.take("result");
                        if (result_value.isString())
                        {
                            //转换result
                            result = result_value.toVariant().toString();
                        }
                    }
                    if (obj.contains("mode"))
                    {
                        //的到mode
                        QJsonValue mode_value = obj.take("mode");
                        if (mode_value.isString())
                        {
                            //转换type
                            mode = mode_value.toVariant().toString();
                        }
                        if(mode=="summer")
                            Login::mode_trans=0;
                        if(mode=="winter")
                            Login::mode_trans=1;
                    }

                }
            }
            qDebug() <<"我日你妈："<< mode<<","<<result << "," << type;

    if(result=="succeed")
        accept();
    else if(result=="failed")
    {
        QMessageBox::warning(this, "Warning!","User name or password error!", QMessageBox::Yes);
        ui->account_number_in->clear();
        ui->password_in->clear();
        ui->account_number_in->setFocus();
    }

}
int Login::mode_trans=0;
