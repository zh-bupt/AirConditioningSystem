#include "mainwindow.h"
#include <QApplication>
#include "login.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    Login dlg;
    if(dlg.exec() == QDialog::Accepted)
    {
        w.show();
        return a.exec();
    }
    else return 0;
}
