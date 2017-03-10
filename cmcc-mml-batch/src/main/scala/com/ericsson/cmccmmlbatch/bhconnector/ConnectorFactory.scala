package com.ericsson.cmccmmlbatch.bhconnector

object ConnectorFactory {
  def apply() = new ConnectorFactory
}

class ConnectorFactory extends IConnectorFactory {
  override def produceCreator(file: String, pool: BHConnectorPool) = new BHCreator(file, pool)

  override def produceCancel(file: String) = new BHCancel(file)

  override def producePauser(file: String) = new BHPauser(file)

  override def produceResumer(file: String) = new BHResumer(file)
}