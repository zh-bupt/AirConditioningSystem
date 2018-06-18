#include "mainwindow.h"
#include <QApplication>
#include "login.h"
#include "socket.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
   // MainWindow w;
    Login *dlg= new Login() ;
    if(dlg->exec ()== QDialog::Accepted)
    {   dlg->close();
        delete dlg;
        MainWindow w;
        w.show();
        return a.exec();
    }
    else return 0;
}
