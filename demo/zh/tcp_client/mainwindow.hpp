#ifndef MAINWINDOW_HPP
#define MAINWINDOW_HPP

#include <QMainWindow>
#include <qtcpsocket.h>
#include <string>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    static std::string getHead(int length);

private slots:
    void receiveData();
    void displayError(QAbstractSocket::SocketError);

    void on_loginBtn_clicked();

    void on_pushButton_clicked();

private:
    void login(QString id, QString password);
    void init();
    void newConnect();
    Ui::MainWindow *ui;
    QTcpSocket *socket;
};

#endif // MAINWINDOW_HPP
