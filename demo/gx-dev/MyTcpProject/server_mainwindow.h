#ifndef SERVER_MAINWINDOW_H
#define SERVER_MAINWINDOW_H

#include <QMainWindow>
#include <QTcpServer>
#include <QTcpSocket>
#include <QDebug>
#include <QCloseEvent>

namespace Ui {
class Server_MainWindow;
}

class Server_MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit Server_MainWindow(QWidget *parent = 0);
    ~Server_MainWindow();

private:
    Ui::Server_MainWindow *ui;
    QTcpServer *server;
    QTcpSocket *socket;
protected:
    void closeEvent(QCloseEvent *event);
private slots:
    void newConnection();
    void readyRead();
    void disconnected();


    void on_pushButton_Listen_clicked();
    void on_pushButton_Send_clicked();
};

#endif // SERVER_MAINWINDOW_H
