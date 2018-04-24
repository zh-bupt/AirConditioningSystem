#ifndef MAINWINDOW_HPP
#define MAINWINDOW_HPP

#include <QMainWindow>
#include "myserver.hpp"

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
    void on_broadcastBtn_clicked();
    void on_sendBtn_clicked();

private:
    myServer *server = nullptr;
    Ui::MainWindow *ui;
};

#endif // MAINWINDOW_HPP