#include "myserver.hpp"
#include "processor.hpp"

myServer::myServer()
{

}

void myServer::incomingConnection(qintptr socketDescriptor)
{
    myThread *thread = new myThread(socketDescriptor, this);
    Processor *processor = new Processor(thread->socket);
    connect(thread->socket, SIGNAL(readyRead()), processor, SLOT(work()));
    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    processor->moveToThread(thread);
    thread->start();
}
