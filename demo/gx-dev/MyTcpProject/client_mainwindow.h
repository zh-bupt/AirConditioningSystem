#ifndef CLIENT_MAINWINDOW_H
#define CLIENT_MAINWINDOW_H

#include <QMainWindow>
#include <QTcpSocket>
#include <QDebug>
#include <QCloseEvent>

namespace Ui {
class Client_MainWindow;
}

class Client_MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit Client_MainWindow(QWidget *parent = 0);
    ~Client_MainWindow();

private:
    Ui::Client_MainWindow *ui;
    QTcpSocket *socket;
protected:
    void closeEvent(QCloseEvent *event);

private slots:
    void readyRead();
    void disconnected();

    void on_pushButton_Connect_clicked();
    void on_pushButton_Send_clicked();
};

#endif // CLIENT_MAINWINDOW_H
