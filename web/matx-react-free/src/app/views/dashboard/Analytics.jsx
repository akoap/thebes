import React, { Fragment, useState } from 'react'
import { Grid, TextField, Button } from '@material-ui/core'
import TopSellingTable from './shared/TopSellingTable'
import { useTheme } from '@material-ui/styles'
import Snackbar from "@material-ui/core/Snackbar";
import Alert from '@material-ui/lab/Alert';

const Analytics = () => {
    const theme = useTheme()
    
    const [open, setOpen] = React.useState(false);
    function handleClose(event, reason) {
        if (reason === "clickaway") {
            return;
        }
        setOpen(false);
    }

    // these constantly store the current inputted values
    const [ name, setName ] = useState("");
    const [ description, setDescription ] = useState("");
    const [ couponType, setCouponType ] = useState("");
    const [ expiration, setExpiration ] = useState("");
    const [ chipId, setChipId ] = useState("");

    function reset() {
        setName("");
        setDescription("");
        setCouponType("");
        setExpiration("");
        setChipId("");
    }

    async function handleSubmit(e) {
        setOpen(true);

        console.log(name, description, couponType, expiration, chipId)

        reset();
    }

    return (
        <Fragment>
            <div className="analytics m-sm-30 mt-6">
                <Grid container spacing={3}>
                    {/* This Grid is for businesses to add coupons */}
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        <TextField id="filled-basic" label="Name" variant="filled" value={name} onChange={(e) => setName(e.target.value)} />
                        <TextField id="filled-basic" label="Description" variant="filled" value={description} onChange={(e) => setDescription(e.target.value)} />
                        <TextField id="filled-basic" label="Coupon Type" variant="filled" value={couponType} onChange={(e) => setCouponType(e.target.value)} />
                        <TextField id="filled-basic" label="Expiration" variant="filled" value={expiration} onChange={(e) => setExpiration(e.target.value)} />
                        <TextField id="filled-basic" label="Chip Id" variant="filled" value={chipId} onChange={(e) => setChipId(e.target.value)} />
                        <br />
                        <Button onClick={handleSubmit} variant="contained">Generate Coupon</Button>
                        {/* "name":"$4 Off $40 total purchase",
                        "description":"$4 Off $40 total purchase",
                        "couponType":"Store",
                        "effective":"2014-08-17",
                        "expiration":"2014-09-21T23:59:59", */}
                    </Grid>
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        {/* <StatCards /> */}

                        {/* Top Selling Products */}
                        <TopSellingTable />

                        {/* <StatCards2 /> */}

                        {/* <h4 className="card-title text-muted mb-4">
                            Ongoing Projects
                        </h4>
                        <RowCards /> */}
                    </Grid>

                    {/* <Grid item lg={4} md={4} sm={12} xs={12}>
                        <Card className="px-6 py-4 mb-6">
                            <div className="card-title">Traffic Sources</div>
                            <div className="card-subtitle">Last 30 days</div>
                            <DoughnutChart
                                height="300px"
                                color={[
                                    theme.palette.primary.dark,
                                    theme.palette.primary.main,
                                    theme.palette.primary.light,
                                ]}
                            />
                        </Card>

                        <UpgradeCard />

                        <Campaigns />
                    </Grid> */}
                </Grid>

                <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
                    <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                        Successfully generated coupon: {}!
                    </Alert>
                </Snackbar>
            </div>
        </Fragment>
    )
}

export default Analytics
