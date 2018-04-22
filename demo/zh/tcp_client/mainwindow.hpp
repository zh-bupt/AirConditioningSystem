#ifndef MAINWINDOW_HPP
#define MAINWINDOW_HPP

#include <QMainWindow>
#include <qtcpsocket.h>

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
    void receiveData();
    void displayError(QAbstractSocket::SocketError);

    void on_send_btn_clicked();

private:
    void init();
    void newConnect();
    Ui::MainWindow *ui;
    QTcpSocket *socket;
};

#endif // MAINWINDOW_HPP
