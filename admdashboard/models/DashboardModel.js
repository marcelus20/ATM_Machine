/**
Creating the Default Dashboard model.
*/
class DashboardModel {
  constructor(fifties = 0, twenties = 0, tenners = 0, fivers = 0) {
    this.fifties = fifties;
    this.twenties = twenties;
    this.tenners = tenners;
    this.fivers = fivers;
    this.total = fifties * 50 + twenties * 20 + tenners * 10 + fivers * 10;
  }
}

export default DashboardModel;
