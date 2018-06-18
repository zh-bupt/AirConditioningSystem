#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include <QMainWindow>
#include "socket.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_change_tem_dial_actionTriggered(int action);

    void on_change_tem_bt_clicked();
   // void displayError(QAbstractSocket::SocketError);
    void readMessages();
//    void readMessages_stop();
    void on_pushButton_clicked();
//    void readMessages_state();
//    void readMessages_bill();
//    void readMessages_check();
    void timerupdate();
private:
     Ui::MainWindow *ui;
     QTcpSocket *tcpSocket1;
     int current_t=0;
     int current_s=0;
     int current_c=0;

};

#endif // MAINWINDOW_H
