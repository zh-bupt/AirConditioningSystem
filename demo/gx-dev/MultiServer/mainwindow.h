#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "myserver.h"
class MyServer;
namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    MyServer *Server;

private:
    Ui::MainWindow *ui;

public slots:
    void readyRead(QByteArray Data, int socketDescriptor);
private slots:
    void on_pushButton_clicked();
    void on_pushButton_Listen_clicked();

signals:
    void sendMsg(QByteArray Data,int socketDescriptor);
};

#endif // MAINWINDOW_H
