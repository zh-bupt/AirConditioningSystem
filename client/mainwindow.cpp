#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "QDebug"
#include "socket.h"
#include "header.h"
#include "QJsonDocument"
#include "QJsonObject"
#include "string.h"
#include "QMessageBox"
#include "QObject"
#include "QString"
#include "iostream"
#include "QTime"
#include "QTimer"
#include "QDateTime"
#include "login.h"



MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    Socket *socket =Socket::Instance();
    this->tcpSocket1 = socket->getSocket();
    connect(tcpSocket1,SIGNAL(readyRead()),this,SLOT(readMessages()));
//    connect(tcpSocket1,SIGNAL(readyRead()),this,SLOT(readMessages_stop()));
//    connect(tcpSocket1,SIGNAL(readyRead()),this,SLOT(readMessages_state()));
//    connect(tcpSocket1,SIGNAL(readyRead()),this,SLOT(readMessages_bill()));
//    connect(tcpSocket1,SIGNAL(readyRead()),this,SLOT(readMessages_check()));
    ui->target_tem->display(18);
    ui->level->display(1);
    if(Login::mode_trans==0){
        ui->current_tem_lcd->display(30);
        current_c=1;
        current_s=1;
        int current_temp;//测试
        current_temp=ui->current_tem_lcd->value();
       //ui->current_tem_lcd->display(30);
        ui->target_tem_lcd->display(22);
        ui->leve_lcd->display(1);
        QJsonObject simp_ayjson;
           simp_ayjson.insert("type", "wind_request");
           simp_ayjson.insert("wind_power","low" );
           simp_ayjson.insert("target_temp", 22);
           simp_ayjson.insert("current_temp", current_temp);
           simp_ayjson.insert("seq", 3);
           QJsonDocument document;
           document.setObject(simp_ayjson);
           QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
           std::string data = QString(simpbyte_array).toStdString();
           std::string login_sent= Header::getHead(data.length())+data;
            qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
           //Socket::write(QString::fromStdString(login_sent).toUtf8());
           tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
           // ui->login->setEnabled(false);

    }
    else{
         ui->current_tem_lcd->display(15);
        current_c=1;
        current_s=1;
        int current_temp;//测试
        current_temp=ui->current_tem_lcd->value();
       //ui->current_tem_lcd->display(30);
        ui->target_tem_lcd->display(28);
        ui->leve_lcd->display(1);
        QJsonObject simp_ayjson;
           simp_ayjson.insert("type", "wind_request");
           simp_ayjson.insert("wind_power","low" );
           simp_ayjson.insert("target_temp", 28);
           simp_ayjson.insert("current_temp", current_temp);
           simp_ayjson.insert("seq", 3);
           QJsonDocument document;
           document.setObject(simp_ayjson);
           QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
           std::string data = QString(simpbyte_array).toStdString();
           std::string login_sent= Header::getHead(data.length())+data;
            qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
           //Socket::write(QString::fromStdString(login_sent).toUtf8());
           tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
           // ui->login->setEnabled(false);



    }
     QTimer*timer =new QTimer(this);
     connect(timer,&QTimer::timeout, this, &MainWindow::timerupdate);
     timer->start(1000);


}

MainWindow::~MainWindow()
{
    delete ui;
    delete this->tcpSocket1;
}
void MainWindow::on_change_tem_dial_actionTriggered(int action)
{

}
void MainWindow::on_change_tem_bt_clicked()
{
    current_c=1;
    int  target_temp;
    int  current_temp;//测试
    int wind_power_dial;
    QString wind_power;
    current_temp=ui->current_tem_lcd->value();
    target_temp=ui->change_tem_dial->value();
    wind_power_dial=ui->change_level->value();
   //ui->current_tem_lcd->display(30);
   ui->target_tem_lcd->display(target_temp);
   ui->leve_lcd->display(wind_power_dial);
    //current_t=1;//测试
    if(wind_power_dial==3)
       { wind_power="high";
        current_s=3;}
    else if(wind_power_dial==2)
        {wind_power="medium";
        current_s=2;}
    else
       { wind_power="low";
        current_s=1;}

    QJsonObject simp_ayjson;
       simp_ayjson.insert("type", "wind_request");
       simp_ayjson.insert("wind_power",wind_power );
       simp_ayjson.insert("target_temp", target_temp);
       simp_ayjson.insert("current_temp", current_temp);
       simp_ayjson.insert("seq", 3);
       QJsonDocument document;
       document.setObject(simp_ayjson);
       QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
       std::string data = QString(simpbyte_array).toStdString();
       std::string login_sent= Header::getHead(data.length())+data;
        qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
       //Socket::write(QString::fromStdString(login_sent).toUtf8());
       tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
       // ui->login->setEnabled(false);


}
void MainWindow::readMessages()
{
    QString data=tcpSocket1->readAll();
    data = data.mid(4);
    // std::string data=tcpSocket1->readAll();
    QJsonParseError simp_json_error;
    QString type;
    int accept;
    // std::string data1=QString(QString::fromStdString((data)).toUtf8()).toStdString();
    // QString simpjson_str= QString::fromStdString(data);
    QString simpjson_str=data;
    qDebug() << "我日他啊 : " << simpjson_str ;
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
                    if(type=="wind_request_ack")
                    {
                        int accept;

                        if (obj.contains("accept")) {
                                   QJsonValue accept_value = obj.value("accept");
                                   if (accept_value.isDouble()) {
                                       accept = accept_value.toVariant().toInt();
                                       qDebug() << "accept : " << accept;
                                   }
                               }

                        if(accept==1)
                           {
                            //QMessageBox::information(this, "Succeed","Succeed!", QMessageBox::Yes);
                            current_t=1;
                            qDebug()<<"current_t:"<<current_t;

                            }

                        else if(accept==0)
                        {
                            QMessageBox::warning(this, "Warning!","Failed!", QMessageBox::Yes);

                        }

                    }
                    if(type=="stop_wind_ack")
                    {

                            //QMessageBox::information(this, "Succeed","Stop Succeed!", QMessageBox::Yes);
                                //温度函数，当前温度会变化，还没有写
                            current_t=0;

                    }

                    if(type=="state_query")
                    {
                        int  target_temp;
                        int  current_temp;
                        int wind_power_dial;
                        int seq;
                        QString wind_power;
                        if (obj.contains("seq")) {
                                   QJsonValue seq_value = obj.value("seq");
                                   if (seq_value.isDouble()) {
                                        seq = seq_value.toVariant().toInt();
                                       qDebug() << "seq : " << seq;
                                   }
                               }
                        target_temp=ui->target_tem_lcd->value();
                        current_temp=ui->current_tem_lcd->value();
                        wind_power_dial=ui->leve_lcd->value();
                        if(wind_power_dial==3)
                            wind_power="high";
                        else if(wind_power_dial==2)
                            wind_power="medium";
                        else
                            wind_power="low";

                        QJsonObject simp_ayjson;
                           simp_ayjson.insert("type", "state_query_ack");
                           simp_ayjson.insert("current_tmp",current_temp );
                           simp_ayjson.insert("target_tmp", target_temp);
                            simp_ayjson.insert("wind_power", wind_power);
                            simp_ayjson.insert("seq",seq);
                           QJsonDocument document;
                           document.setObject(simp_ayjson);
                           QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
                           std::string data = QString(simpbyte_array).toStdString();
                           std::string login_sent= Header::getHead(data.length())+data;
                            qDebug()<< "状态ack:"<< QString::fromStdString(login_sent);
                           //Socket::write(QString::fromStdString(login_sent).toUtf8());
                           tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
                           // ui->login->setEnabled(false);

                    }
                    if(type=="bill")
                    {


                        if (obj.contains("money")) {
                                  QJsonValue money_value = obj.value("money");
                                   if (money_value.isDouble()) {
                                       double money = money_value.toVariant().toDouble() ;
                                      ui->money_lcd->display(money);
                                       qDebug() << "money : " << money;
                                   }
                               }
                        if (obj.contains("power")) {
                                   QJsonValue power_value = obj.value("power");
                                   if (power_value.isDouble()) {
                                       double power = power_value.toVariant().toDouble() ;
                                       ui->power_lcd->display(power);
                                       qDebug() << "power : " << power;
                                   }
                               }
                           QJsonObject simp_ayjson;
                           simp_ayjson.insert("type", "bill_ack");
                           QJsonDocument document;
                           document.setObject(simp_ayjson);
                           QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
                           std::string data = QString(simpbyte_array).toStdString();
                           std::string login_sent= Header::getHead(data.length())+data;
                            qDebug()<< "bill数据:"<< QString::fromStdString(login_sent);
                           //Socket::write(QString::fromStdString(login_sent).toUtf8());
                           tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
                           // ui->login->setEnabled(false);



                    }
                    if(type=="check")
                    {

                            QMessageBox::information(this, "Succeed","Stop Succeed!", QMessageBox::Yes);
                                //温度函数，当前温度会变化，还没有写
                              current_t=0;
                              current_c=0;
                    }
                }
     }

}
void MainWindow::on_pushButton_clicked()
{


    QJsonObject simp_ayjson;
       simp_ayjson.insert("type", "stop_wind");
       simp_ayjson.insert("seq", 4);
       QJsonDocument document;
       document.setObject(simp_ayjson);
       QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
       std::string data = QString(simpbyte_array).toStdString();
       std::string login_sent= Header::getHead(data.length())+data;
        qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
       //Socket::write(QString::fromStdString(login_sent).toUtf8());
       tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
       // ui->login->setEnabled(false);
       current_c=0;

}
void MainWindow::timerupdate(){
    double  current_tem;
    double  target_tem;
     //夏天温度函数
    current_tem=ui->current_tem_lcd->value();
    target_tem=ui->target_tem_lcd->value();
    if(Login::mode_trans==0)
    {
       //升温函数y=kt+tem_first_time,直到温度变为30度，降温时y=tem_first_time-kt
       if(current_t==0&&current_tem<30)
       {
           current_tem=current_tem+0.5;
           if(current_tem>30)
           ui->current_tem_lcd->display(30);
           else
           ui->current_tem_lcd->display(current_tem);

       }
       if(current_c==1&&current_t==1&&target_tem>current_tem)
       {
           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "stop_wind");
              simp_ayjson.insert("seq", 4);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
              // ui->login->setEnabled(false);
              current_c=0;
       }
       if(current_t==1&&target_tem<current_tem)
       {

           if(current_s==1)
               current_tem=current_tem-0.3;
           if(current_s==2)
               current_tem=current_tem-0.5;
           if(current_s==3)
               current_tem=current_tem-0.7;
           if(current_tem<=target_tem)
           {
           ui->current_tem_lcd->display(target_tem);
           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "stop_wind");
              simp_ayjson.insert("seq", 4);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "能不能给个结果啊我也是醉了:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
              // ui->login->setEnabled(false);
              //current_t=0;
           }
           else
           ui->current_tem_lcd->display(current_tem);
           qDebug()<<"我的温度函数:"<<current_tem;
       }
       if(current_t==0&&current_c==1&&(current_tem-target_tem)>=2)
       {
           //current_t=1;

           int wind_power_dial;
           int current_tem_int;
           int target_tem_int;
           QString wind_power;
           current_tem=ui->current_tem_lcd->value();
           target_tem=ui->change_tem_dial->value();
           current_tem_int=current_tem;
           target_tem_int=target_tem;
           wind_power_dial=ui->change_level->value();
          //ui->current_tem_lcd->display(30);
           //current_t=1;//测试
           if(wind_power_dial==3)
              { wind_power="high";
               current_s=3;}
           else if(wind_power_dial==2)
               {wind_power="medium";
               current_s=2;}
           else
              { wind_power="low";
               current_s=1;}

           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "wind_request");
              simp_ayjson.insert("wind_power",wind_power );
              simp_ayjson.insert("target_temp", target_tem_int);
              simp_ayjson.insert("current_temp", current_tem_int);
              simp_ayjson.insert("seq", 3);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
       }
    }

    if(Login::mode_trans==1)
    {
       //降温函数y=-kt+tem_first_time,直到温度变为15度，升温时y=tem_first_time+kt
       if(current_t==0&&current_tem>15)
       {
           current_tem=current_tem-0.5;
           if(current_tem<15)
           ui->current_tem_lcd->display(15);
           else
           ui->current_tem_lcd->display(current_tem);

       }
       if(current_c==1&&current_t==1&&target_tem<current_tem)
       {
           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "stop_wind");
              simp_ayjson.insert("seq", 4);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
              // ui->login->setEnabled(false);
              current_c=0;
       }
       if(current_t==1&&target_tem>current_tem)
       {
           if(current_s==1)
               current_tem=current_tem+0.3;
           if(current_s==2)
               current_tem=current_tem+0.5;
           if(current_s==3)
               current_tem=current_tem+0.7;
           if(current_tem>=target_tem)
            {
           ui->current_tem_lcd->display(target_tem);
           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "stop_wind");
              simp_ayjson.insert("seq", 4);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "检查下错误:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
              // ui->login->setEnabled(false);
              //current_t=0;

           }
           else
           ui->current_tem_lcd->display(current_tem);
       }
       double chazhi=(target_tem-current_tem);
       if(current_t==0&&current_c==1&&chazhi>=2)
       {
          // current_t=1;
           int wind_power_dial;
           int current_tem_int;
           int target_tem_int;

           QString wind_power;
           current_tem=ui->current_tem_lcd->value();
           target_tem=ui->change_tem_dial->value();
           target_tem_int=target_tem;
           current_tem_int=current_tem;
           wind_power_dial=ui->change_level->value();
          //ui->current_tem_lcd->display(30);
           //current_t=1;//测试
           if(wind_power_dial==3)
              { wind_power="high";
               current_s=3;}
           else if(wind_power_dial==2)
               {wind_power="medium";
               current_s=2;}
           else
              { wind_power="low";
               current_s=1;}

           QJsonObject simp_ayjson;
              simp_ayjson.insert("type", "wind_request");
              simp_ayjson.insert("wind_power",wind_power );
              simp_ayjson.insert("target_temp", target_tem_int);
              simp_ayjson.insert("current_temp", current_tem_int);
              simp_ayjson.insert("seq", 3);
              QJsonDocument document;
              document.setObject(simp_ayjson);
              QByteArray simpbyte_array = document.toJson(QJsonDocument::Compact);
              std::string data = QString(simpbyte_array).toStdString();
              std::string login_sent= Header::getHead(data.length())+data;
               qDebug()<< "简单的QTJson数据:"<< QString::fromStdString(login_sent);
              //Socket::write(QString::fromStdString(login_sent).toUtf8());
              tcpSocket1->write(QString::fromStdString(login_sent).toUtf8());
       }

    }



}
