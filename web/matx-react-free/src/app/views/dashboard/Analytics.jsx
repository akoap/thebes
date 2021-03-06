import React, { Fragment, useState } from 'react'
import { Grid, TextField, Button } from '@material-ui/core'
import TopSellingTable from './shared/TopSellingTable'
import Snackbar from "@material-ui/core/Snackbar";
import Alert from '@material-ui/lab/Alert';

const Analytics = () => {    
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
    const [ message, setMessage ] = useState("");

    function reset() {
        setName("");
        setDescription("");
        setCouponType("");
        setExpiration("");
        setChipId("");
    }

    async function handleSubmit(e) {
        // console.log(name, description, couponType, expiration, chipId)

        const couponEndpoint = 'https://us-east1-thebes-329917.cloudfunctions.net/thebes-backend?first=' + name + '&second=' + description + '&third=' + expiration;

        fetch(couponEndpoint)
        .then(resonse => resonse.text())
        .then(data => {
            setMessage(data);
            setOpen(true);
        });

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
                    </Grid>
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        <TopSellingTable />
                    </Grid>
                </Grid>

                <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
                    <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                        Successfully generated coupon: {message}
                    </Alert>
                </Snackbar>
            </div>
        </Fragment>
    )
}

export default Analytics
