class Bar extends React.Component {
    render() {
        return <td className="bar" style={{
            // background: "rgb(" +
            //     (this.props.milliseconds <= 0 ? 0 : Math.floor(this.props.milliseconds / this.props.maxMilliseconds * 200)) + "," +
            //     (this.props.milliseconds <= 0 ? 255 : Math.floor(255 - this.props.milliseconds / this.props.maxMilliseconds * 128)) + "," +
            //     "0)",
            width: (this.props.milliseconds <= 0 ? 0 : Math.floor(200 * (this.props.milliseconds / this.props.maxMilliseconds))) + "px"
        }}>
        </td>;
    }
}

class Milliseconds extends React.Component {
    render() {
        return <td className="milliseconds">{
            this.props.milliseconds == 0 ? "..." :
                (this.props.milliseconds < 0 ? -this.props.milliseconds + "/3" : this.props.milliseconds + " ms")}
        </td>;
    }
}

class Name extends React.Component {
    render() {
        return <td className="name">
            {this.props.locationName}
        </td>;
    }
}

class Line extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <tr className={"line"}>
            <Name locationName={this.props.name}
                  provider={this.props.provider}
                  url={this.props.url}
                  key={this.props.index}
            />
            <Milliseconds milliseconds={this.props.milliseconds}/>
        </tr>;
    }
}

class List extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            millisecondsOfTargets: new Array(),
            rankList: new Array()
        };
        this.data = {
            img: document.createElement("img"),
            testTimeOut: false,
            timeOutId: null,
            currentIndex: 0,
            countOfLoad: 0,
            startTime: 0,
            endTime: 0,
            minOfCurrentTarget: 60000
        };
        document.body.appendChild(this.data.img);
        this.lineRef = new Array();
        for (let i = 0; i <= Object.keys(this.props.targets).length - 1; i++) {
            this.lineRef[i] = React.createRef();
        }
        for (let i = 0; i <= Object.keys(this.props.targets).length - 1; i++) {
            this.state.millisecondsOfTargets[i] = 0;
        }
        for (let i = 0; i <= Object.keys(this.props.targets).length - 1; i++) {
            this.state.rankList[i] = {id: i, rank: i, milliseconds: 60000 + i};
        }
        this.onTimeOut = this.onTimeOut.bind(this);
        this.onLoaded = this.onLoaded.bind(this);
    }

    updateCount() {
        let newMillisecondsOfTargets = this.state.millisecondsOfTargets.slice();
        newMillisecondsOfTargets[this.data.currentIndex] = -this.data.countOfLoad;
        this.setState({millisecondsOfTargets: newMillisecondsOfTargets});
    }

    onTimeOut() {
        const callback = this.data.img.onerror;
        this.data.img.onerror = null;
        this.data.img.src = "";
        setTimeout(callback, 1);
    }

    clearThisTimeOut() {
        try {
            clearTimeout(this.data.timeOutId);
        } catch (e) {
            console.log("clearThisTimeOut ERROR");
        }
    }

    prepareTimeOut() {
        this.clearThisTimeOut();
        this.data.timeOutId = setTimeout(this.onTimeOut, 6000);
    }

    doNextTest() {
        const currentLine = this.lineRef[this.data.currentIndex];
        this.data.countOfLoad++;
        this.updateCount();
        this.data.startTime = new Date().getTime();
        this.data.img.onerror = this.onLoaded;
        this.data.img.style = "display:none;"
        this.prepareTimeOut();
        if (!this.data.testTimeOut) {
            this.data.img.src = currentLine.current.props.url + "" + Math.random();
        }
    }

    doNextTarget() {
        this.data.currentIndex++;
        if (this.data.currentIndex > this.lineRef.length - 1) {
            this.props.providerFinished();
            this.clearThisTimeOut();
            return;
        }
        const currentLine = this.lineRef[this.data.currentIndex];
        this.data.countOfLoad = 1;
        this.updateCount();
        this.data.startTime = new Date().getTime();
        this.data.img.onerror = this.onLoaded;
        this.data.img.style = "display:none;"
        this.prepareTimeOut();
        if (!this.data.testTimeOut) {
            this.data.img.src = currentLine.current.props.url + "" + Math.random();

        }
    }

    onLoaded() {
        if (this.data.countOfLoad == 1) {
            this.doNextTest();
            return;
        }
        this.data.endTime = new Date().getTime();
        const currentCostOfTime = this.data.endTime - this.data.startTime;
        if (currentCostOfTime < this.data.minOfCurrentTarget) {
            this.data.minOfCurrentTarget = currentCostOfTime;
        }
        if (this.data.countOfLoad == 3) {
            let newMillisecondsOfTargets = this.state.millisecondsOfTargets.slice();
            newMillisecondsOfTargets[this.data.currentIndex] = this.data.minOfCurrentTarget;
            this.setState({millisecondsOfTargets: newMillisecondsOfTargets});
            let newRankListWithNewData = JSON.parse(JSON.stringify(this.state.rankList));
            newRankListWithNewData[this.data.currentIndex].milliseconds = this.data.minOfCurrentTarget;
            let newRankListSort = JSON.parse(JSON.stringify(newRankListWithNewData));
            newRankListSort.sort(function (a, b) {
                return a.milliseconds - b.milliseconds;
            });
            let newRankListForUpdate = JSON.parse(JSON.stringify(newRankListWithNewData));
            for (let i = 0; i <= newRankListForUpdate.length - 1; i++) {
                const currentId = newRankListForUpdate[i].id;
                for (let j = 0; j <= newRankListSort.length; j++) {
                    if (currentId == newRankListSort[j].id) {
                        newRankListForUpdate[i].rank = j;
                        break;
                    }
                }
            }
            this.setState({rankList: newRankListForUpdate});
            this.data.minOfCurrentTarget = 60000;
            this.doNextTarget();
            return;
        }
        this.doNextTest();
    }

    componentDidMount() {
        const currentLine = this.lineRef[this.data.currentIndex];
        this.data.countOfLoad = 1;
        this.updateCount();
        this.data.startTime = new Date().getTime();
        this.data.img.onerror = this.onLoaded;
        this.prepareTimeOut();
        if (!this.data.testTimeOut) {
            this.data.img.src = currentLine.current.props.url + Math.random();
        }
    }

    render() {
        return <tbody className={"list"}>{this.props.targets.map(function (item, index) {
            return <Line
                name={item.name}
                key={index}
                url={item.url}
                milliseconds={this.state.millisecondsOfTargets[index]}
                maxMilliseconds={Math.max(...this.state.millisecondsOfTargets)}
                index={this.state.rankList[index].rank}
                number={this.state.rankList.length}
                provider={this.props.provider}
                showNotice={this.props.showNotice}
                ref={this.lineRef[index]}/>;
        }.bind(this))}</tbody>;
    }
}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allData: data, messageVisible: false, noticeVisible: false,
            clickedProvider: "", clickedName: "", clickedServer: "", clickedDownload: ""
        };
        this.data = {countOfFinishedProviders: 0};
        this.providerFinished = this.providerFinished.bind(this);
        this.allProvidersFinished = this.allProvidersFinished.bind(this);
    }

    providerFinished() {
        this.data.countOfFinishedProviders++;
    }

    allProvidersFinished() {
        return (this.data.countOfFinishedProviders == Object.keys(this.state.allData).length);
    }

    render() {
        return <div>
            {Object.keys(this.state.allData).map(function (item, index) {
                const sectionStyle = {
                    // height: this.state.allData[item].length * (18 + 2) + 30 + "px",
                    "marginBottom": "0px"
                };
                return (
                    <table className={"table"} style={sectionStyle}>
                        <thead>
                        <tr>
                            <th>Location</th>
                            <th>延迟测试</th>
                        </tr>
                        </thead>
                        <List
                            provider={item}
                            providerFinished={this.providerFinished}
                            targets={this.state.allData[item]}/>
                    </table>
                );
            }.bind(this))}
        </div>;
    }
}

function main() {
    ReactDOM.render(<App/>, document.getElementById("result"));
}