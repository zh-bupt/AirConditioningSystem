#ifndef TCPSOCKET_H
#define TCPSOCKET_H
#include "tcpsocket.h"
class socket{
public:
    static socket* Instance();
protected:
    socket();
private:
    static socket* _instance;

};

#endif // TCPSOCKET_H

