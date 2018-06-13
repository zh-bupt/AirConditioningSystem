#ifndef LOGIN_H
#define LOGIN_H
#include <QTcpSocket>
#include <QDialog>
#include "QMessageBox"
#include "QtNetwork"

namespace Ui {
class Login;
}

class Login : public QDialog
{
    Q_OBJECT

public:
    explicit Login(QWidget *parent = 0);
    ~Login();

protected:
    void init();
    void connectServer();

private slots:
    void on_login_clicked();
    //void socket_Read_Data();
    //void socket_Disconnected();
    void displayError(QAbstractSocket::SocketError);
    void readMessages();
private:
    Ui::Login *ui;
    QTcpSocket *tcpSocket;

};

#endif // LOGIN_H
